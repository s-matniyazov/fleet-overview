package fleetoverview.domain.projection;

import fleetoverview.domain.entity.StateEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDto {

    private Integer id;
    private String name;
    private Integer countryId;

    public StateDto(StateEntity state) {
        this.id= state.getId();
        this.name = state.getName();
        this.countryId= state.getCountry().getId();
    }
}