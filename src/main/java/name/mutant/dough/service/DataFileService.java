package name.mutant.dough.service;

import name.mutant.dough.DoughNotFoundException;
import name.mutant.dough.DoughOptimisticLockingException;
import name.mutant.dough.domain.DataFile;
import name.mutant.dough.repository.DataFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataFileService {
    @Autowired
    private DataFileRepository dataFileRepository;

    public Iterable<DataFile> findAllDataFiles() {
        return dataFileRepository.findAll();
    }

    public DataFile findDataFileById(Long dataFileId) throws DoughNotFoundException {
        Optional<DataFile> optional = dataFileRepository.findById(dataFileId);
        if (!optional.isPresent()) {
            throw new DoughNotFoundException("DataFile not found, dataFileId=\"" + dataFileId + "\"");
        }
        return optional.get();
    }

    public DataFile saveDataFile(DataFile dataFile) throws DoughOptimisticLockingException {
        try {
            return dataFileRepository.save(dataFile);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoughOptimisticLockingException(
                    "Optimistic locking failure while saving dataFile, dataFileId=\"" + dataFile.getId() + "\"", e);
        }
    }
}