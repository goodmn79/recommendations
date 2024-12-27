package pro.sky.recommendations.mapper.model_mapper;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.constant.ComparisonOperator;
import pro.sky.recommendations.constant.ProductType;
import pro.sky.recommendations.constant.TransactionType;
import pro.sky.recommendations.dto.QueryDTO;
import pro.sky.recommendations.exception.InvalidQueryDataException;
import pro.sky.recommendations.model.Query;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pro.sky.recommendations.constant.QueryType.*;

@Component
public class QueryMapper {
    private final ModelMapper mapper = new ModelMapper();

    private final Logger log = LoggerFactory.getLogger(QueryMapper.class);

    public QueryMapper() {

        mapper.addMappings(new PropertyMap<QueryDTO, Query>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }

    public Query mapFromDTO(QueryDTO queryDTO) {
        validateQuery(queryDTO);
        Query mappedQuery = mapper.map(queryDTO, Query.class);
        return mappedQuery.setId(UUID.randomUUID());
    }

    public List<Query> mapFromDTO(List<QueryDTO> queryDTOS) {
        queryDTOS.forEach(this::validateQuery);
        Type listType = new TypeToken<ArrayList<Query>>() {
        }.getType();
        List<Query> mappedList = mapper.map(queryDTOS, listType);
        return mappedList
                .stream()
                .map(q -> q.setId(UUID.randomUUID()))
                .toList();
    }

    public QueryDTO mapToDTO(Query query) {
        return mapper.map(query, QueryDTO.class);
    }

    public List<QueryDTO> mapToDTO(List<Query> queries) {
        Type listType = new TypeToken<ArrayList<QueryDTO>>() {
        }.getType();
        return mapper.map(queries, listType);
    }

    private void validateQuery(QueryDTO queryDTO) {
        if (!QUERY_TYPES.containsKey(queryDTO.getQuery())
                || !validArgs(queryDTO)
                || queryDTO.getNegate() == null) {
            throw new InvalidQueryDataException();
        }
    }

    private boolean validArgs(QueryDTO queryDTO) {
        String queryType = queryDTO.getQuery();
        String[] args = queryDTO.getArguments();
        return switch (queryType) {
            case (USER_OF), (ACTIVE_USER_OF) -> args.length == 1
                    && ProductType.PRODUCT_TYPES.contains(args[0]);
            case (TRANSACTION_SUM_COMPARE) -> args.length == 4
                    && ProductType.PRODUCT_TYPES.contains(args[0])
                    && TransactionType.TRANSACTION_TYPES.contains(args[1])
                    && ComparisonOperator.COMPARISON_OPERATORS.contains(args[2])
                    && StringUtils.isNumeric(args[3]);
            case (TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW) -> args.length == 2
                    && ProductType.PRODUCT_TYPES.contains(args[0])
                    && ComparisonOperator.COMPARISON_OPERATORS.contains(args[1]);
            default -> false;
        };
    }
}


