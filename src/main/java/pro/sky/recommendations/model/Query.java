package pro.sky.recommendations.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Query {
    private UUID id;
    private Recommendation recommendation;
    private String query;
    private String[] arguments;
    private Boolean negate;

    public Query stringToArgs(String args) {
        this.arguments = args.split(" ");
        return this;
    }

    public String argsToString() {
        return StringUtils.join(this.arguments, " ");
    }
}
