package pro.sky.recommendations.mapper;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.constant.ComparisonOperator;
import pro.sky.recommendations.constant.ProductType;
import pro.sky.recommendations.constant.TransactionType;
import pro.sky.recommendations.dto.QueryDTO;
import pro.sky.recommendations.exception.InvalidQueryDataException;
import pro.sky.recommendations.model.Query;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.UUID;

import static pro.sky.recommendations.constant.QueryType.*;

@Component
public class QueryMapper {
    private static final ModelMapper mapper = new ModelMapper();

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
        return mapper.map(queryDTO, Query.class)
                .setId(UUID.randomUUID());
    }

    public Collection<Query> mapFromDTO(Collection<QueryDTO> queryDTOS) {
        queryDTOS.forEach(this::validateQuery);
        Type collectionType = new TypeToken<Collection<Query>>() {
        }.getType();
        Collection<Query> queries = mapper.map(queryDTOS, collectionType);
        queries.forEach(query -> query.setId(UUID.randomUUID()));
        return queries;
    }

    public QueryDTO mapToDTO(Query query) {
        return mapper.map(query, QueryDTO.class);
    }

    public Collection<QueryDTO> mapToDTO(Collection<Query> queries) {
        Type collectionType = new TypeToken<Collection<QueryDTO>>() {
        }.getType();
        return mapper.map(queries, collectionType);
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
        String[] args = queryDTO.getArgs();
        return switch (queryType) {
            case (USER_OF), (ACTIVE_USER_OF) -> args.length == 1 && ProductType.PRODUCT_TYPES.contains(args[0]);
            case (TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW) -> args.length == 4
                    && ProductType.PRODUCT_TYPES.contains(args[0])
                    && TransactionType.TRANSACTION_TYPES.contains(args[1])
                    && ComparisonOperator.COMPARISON_OPERATORS.contains(args[2])
                    && StringUtils.isNumeric(args[3]);
            default -> false;
        };
    }
}