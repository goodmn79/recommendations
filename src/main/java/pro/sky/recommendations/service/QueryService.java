package pro.sky.recommendations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.recommendations.dto.QueryDTO;
import pro.sky.recommendations.mapper.model_mapper.QueryMapper;
import pro.sky.recommendations.model.DynamicRule;
import pro.sky.recommendations.model.Query;
import pro.sky.recommendations.repository.QueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository queryRepository;
    private final QueryMapper queryMapper;

    public List<QueryDTO> create(DynamicRule dynamicRule, List<QueryDTO> queryDTO) {
        List<Query> queries = queryMapper.mapFromDTO(queryDTO)
                .stream()
                .map(query -> query.setDynamicRule(dynamicRule))
                .toList();
        queryRepository.saveAll(queries);
        return queryDTO;
    }
}
