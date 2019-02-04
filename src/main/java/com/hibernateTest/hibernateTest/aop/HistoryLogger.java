package com.hibernateTest.hibernateTest.aop;

import com.hibernateTest.hibernateTest.model.ChangeRow;
import com.hibernateTest.hibernateTest.model.MainEntity;
import com.hibernateTest.hibernateTest.service.ChangeRowService;
import com.hibernateTest.hibernateTest.service.DepartmentService;
import com.hibernateTest.hibernateTest.service.EmployeeService;
import com.hibernateTest.hibernateTest.util.MyDateTimeFormatter;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Aspect
public class HistoryLogger {

    private Logger logger = Logger.getLogger(HistoryLogger.class);

    @Autowired
    @Qualifier("defoultDepartmentService")
    private DepartmentService departmentService;

    @Autowired
    @Qualifier("defoultEmployeeService")
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("defaultChangeRowService")
    private ChangeRowService changeRowService;

    @Before("execution(* com.hibernateTest.hibernateTest.service.defaultImplementation.*.delete(*))")
    public void deleteAdvice(JoinPoint joinPoint){
        String method = joinPoint.getSignature().getName();
        Class clazz = joinPoint.getTarget().getClass();
        Object[] arguments = joinPoint.getArgs();
        int id = (int) arguments[0];

        this.logger.info("1.1. Method delete with id " + id + " and class: " + clazz);
        if(id > 0){
            MainEntity mainEntity = this.findEntityById(clazz, id);
            this.logger.info("1.2. Result of findEntityById: " + mainEntity);

            if(mainEntity != null){
                ChangeRow changeRow = this.generateChangeRow(mainEntity, null, method);
                this.changeRowService.addChangeRow(changeRow);
                this.logger.info("1.3. Generated ChangeRow by method generateChangeRow() : " + changeRow);
            }
        }
    }

    @AfterReturning(pointcut = "execution(* com.hibernateTest.hibernateTest.service.defaultImplementation.*.add(*))", returning = "entity")
    public void addAdvice(JoinPoint joinPoint, MainEntity entity){
        String method = joinPoint.getSignature().getName();
        this.logger.info("2.1. Method add with entity " + entity);

        if(entity != null){
            ChangeRow changeRow = generateChangeRow(null, entity, method);
            this.changeRowService.addChangeRow(changeRow);
            this.logger.info("2.2. Generated ChangeRow by method generateChangeRow() : " + changeRow);
        }
    }

    @Around("execution(* com.hibernateTest.hibernateTest.service.defaultImplementation.*.update(*))")
    public void updateAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String method = proceedingJoinPoint.getSignature().getName();
        Class clazz = proceedingJoinPoint.getTarget().getClass();

        Object[] arguments = proceedingJoinPoint.getArgs();
        MainEntity valueFromClient = (MainEntity)arguments[0];
        this.logger.info("3.1. Method update with entity from client: " + valueFromClient + " and class: " + clazz);

        if(valueFromClient != null){
            MainEntity oldValue = this.findEntityById(clazz, valueFromClient.getId());
            MainEntity newValue = (MainEntity)proceedingJoinPoint.proceed();
            this.logger.info("3.2. Old value: " + oldValue + " and new Value: " + newValue);

            if(newValue != null && oldValue != null){
                ChangeRow changeRow = generateChangeRow(oldValue, newValue, method);
                this.changeRowService.addChangeRow(changeRow);
                this.logger.info("3.3. Generated ChangeRow by method generateChangeRow() : " + changeRow);
            }
        }
    }

    private String getUsername(){
        OAuth2Authentication authentication = (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication();
        Authentication token = authentication.getUserAuthentication();

        LinkedHashMap<String, String> userDetails = (LinkedHashMap<String, String>) token.getDetails();

        String email = userDetails.get("email");
        String name = userDetails.get("given_name");
        String surname= userDetails.get("family_name");

        return surname + " " + name + " " + email;
    }

    private ChangeRow generateChangeRow(MainEntity oldValue, MainEntity newValue, String method){
        ChangeRow changeRow = new ChangeRow();
        changeRow.setUsername(this.getUsername());
        changeRow.setDateChange(MyDateTimeFormatter.format(LocalDateTime.now()));
        changeRow.setVersion(newValue.getVersion());
        changeRow.setMethodName(method);
        changeRow.setTableName(newValue.getClass().getSimpleName().toLowerCase() + "s");
        changeRow.setNewValue(newValue.toString());
        changeRow.setOldValue(oldValue.toString());
        return changeRow;
    }

    private MainEntity findEntityById(Class clazz, int id){
        if(EmployeeService.class.isAssignableFrom(clazz)){
            return employeeService.getById(id);
        }else if(DepartmentService.class.isAssignableFrom(clazz)){
            return departmentService.getById(id);
        }
        return null;
    }
}
