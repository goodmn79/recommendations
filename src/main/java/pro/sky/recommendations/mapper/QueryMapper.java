package pro.sky.recommendations.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import pro.sky.recommendations.dto.QueryDTO;
import pro.sky.recommendations.model.Query;

import java.lang.reflect.Type;
import java.util.Collection;

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
        return mapper.map(queryDTO, Query.class);
    }

    public QueryDTO mapToDTO(Query query) {
        return mapper.map(query, QueryDTO.class);
    }

    public Collection<QueryDTO> mapToDTO(Collection<Query> queries) {
        Type collectionType = new TypeToken<Collection<QueryDTO>>() {
        }.getType();
        return mapper.map(queries, collectionType);
    }

    public Collection<Query> mapFromDTO(Collection<QueryDTO> queryDTOS) {
        Type collectionType = new TypeToken<Collection<Query>>() {
        }.getType();
        return mapper.map(queryDTOS, collectionType);
    }
}
