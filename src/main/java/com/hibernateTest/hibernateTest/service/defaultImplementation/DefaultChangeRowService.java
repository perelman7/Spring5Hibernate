package com.hibernateTest.hibernateTest.service.defaultImplementation;

import com.hibernateTest.hibernateTest.model.ChangeRow;
import com.hibernateTest.hibernateTest.repository.ChangeRowRepository;
import com.hibernateTest.hibernateTest.service.ChangeRowService;
import com.hibernateTest.hibernateTest.util.ConvertorIteratorToList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("defaultChangeRowService")
public class DefaultChangeRowService implements ChangeRowService {

    @Autowired
    @Qualifier("changeRowRepository")
    private ChangeRowRepository repository;

    private ConvertorIteratorToList<ChangeRow> convertor = new ConvertorIteratorToList<>();
    private static Logger logger = Logger.getLogger(DefaultChangeRowService.class);

    @Override
    public List<ChangeRow> getAll() {
        List<ChangeRow> changeRows = convertor.convert(repository.findAll());
        if( changeRows.size() <= 0){
            logger.error("Table changeRows is empty");
        }
        return changeRows;
    }

    @Override
    public ChangeRow getById(long id) {
        Optional<ChangeRow> changeRow = repository.findById(id);
        if(!changeRow.isPresent()){
            logger.error("ChangeRow with appointed id does not exist.");
        }
        return changeRow.orElse(new ChangeRow());
    }

    @Override
    public ChangeRow addChangeRow(ChangeRow changeRow) {
        if(changeRow == null){
            logger.error("ChangeRow is null.");
            return new ChangeRow();
        }else {
            return repository.save(changeRow);
        }
    }
}
