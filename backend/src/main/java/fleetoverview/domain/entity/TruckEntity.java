package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "trucks")
public class TruckEntity extends BaseEntity {
    @Column(length = 50)
    private String unit;
    private Date inServiceDate;
    @Column(length = 50)
    private String licensePlate;
    @ManyToOne(targetEntity = CityEntity.class)
    private CityEntity city;
    @ManyToOne(targetEntity = ModelMakerEntity.class)
    private ModelMakerEntity modelMaker;
    private Integer year;
    @ManyToOne(targetEntity = FuelTypeEntity.class)
    private FuelTypeEntity fuelType;
    private Double grossWeight;
    private Integer axles;
    @Column(length = 50)
    private String vin;
    @ManyToOne(targetEntity = OwnershipTypeEntity.class)
    private OwnershipTypeEntity ownershipType;

    private Boolean includeIFTA;
    @ManyToOne(targetEntity = PurchaseTypeEntity.class)
    private PurchaseTypeEntity purchaseType;

    @ManyToOne(targetEntity = OwnerOperatorEntity.class)
    private OwnerOperatorEntity ownerOperator;

    @Column(length = 4000)
    private String description;
}
