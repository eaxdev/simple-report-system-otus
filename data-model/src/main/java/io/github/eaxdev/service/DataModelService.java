package io.github.eaxdev.service;

import io.github.eaxdev.model.DataModel;
import io.github.eaxdev.repository.DataModelRepository;
import io.github.exception.DataModelNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataModelService {

    private final DataModelRepository dataModelRepository;

    @Transactional(readOnly = true)
    public Page<DataModel> findAll(Pageable pageable) {
        return dataModelRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public DataModel findById(int dataModelId) {
        return dataModelRepository.findById(dataModelId).orElseThrow(() -> new DataModelNotFound(dataModelId));
    }

    @Transactional
    public DataModel createOrUpdate(DataModel dataModel) {
        return dataModelRepository.save(dataModel);
    }

    @Transactional
    public void deleteById(int dataModelId) {
        dataModelRepository.deleteById(dataModelId);
    }
}
