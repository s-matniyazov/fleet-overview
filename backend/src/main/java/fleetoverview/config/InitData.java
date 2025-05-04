package fleetoverview.config;

import fleetoverview.domain.entity.*;
import fleetoverview.domain.entity.enums.ActionTypesEnum;
import fleetoverview.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static fleetoverview.util.helper.Utils.getUrls;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 11 февр. 2025
 **/
@Component
public class InitData implements CommandLineRunner {

    public InitData(ActionRepository actionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, OwnershipTypeRepository ownershipTypeRepository, StateRepository statesRepository, FuelTypeRepository fuelRepository, CountryRepository countryRepository, CountryRepository countryRepository1) {
        this.actionRepository = actionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.statesRepository = statesRepository;
        this.fuelTypeRepository = fuelRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) {
        initActions();
        initRoles();
        initUsers();
        initOwnershipTypes();
        initStates();
        initFuelTypes();
        initCountries();
    }

    private final ActionRepository actionRepository;
    private Set<ActionEntity> actions = new HashSet<>();

    private void initActions() {
        if (!actionRepository.existsByUrl("/api")) {
            actions = new HashSet<>(actionRepository.saveAll(
                    getUrls().stream()
                            .map(it -> {
                                String[] split = it.split("/");
                                return new ActionEntity(
                                        split[split.length - 1],
                                        split[split.length - 1],
                                        it,
                                        ActionTypesEnum.ACTION
                                );
                            })
                            .collect(Collectors.toSet())
            ));
        }
    }

    private final RoleRepository roleRepository;

    private void initRoles() {
        if (!roleRepository.existsByName("ADMIN")) {
            roleRepository.saveAll(
                    List.of(
                            new RoleEntity(
                                    "ADMIN",
                                    "admin",
                                    actions
                            ),
                            new RoleEntity(
                                    "USER",
                                    "user",
                                    actions
                            )
                    )
            );
        }
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void initUsers() {
        if (userRepository.findByUsername("username").isEmpty()) {
            userRepository.saveAll(
                    List.of(
                            new UserEntity(
                                    "username",
                                    passwordEncoder.encode("password"),
                                    "Testov User",
                                    "kuchukcha@gmail.com",
                                    roleRepository.getReferenceById(1)
                            )
                    )
            );
        }
    }

    private final OwnershipTypeRepository ownershipTypeRepository;

    private void initOwnershipTypes() {
        if (ownershipTypeRepository.existsById(1)) {
            ownershipTypeRepository.saveAll(
                    List.of(
                            new OwnershipTypeEntity("Company"),
                            new OwnershipTypeEntity("Owner Operator's"),
                            new OwnershipTypeEntity("Contractor Owned")
                    )
            );
        }
    }
private final CountryRepository countryRepository;

    private void initCountries() {
        if (countryRepository.existsById(1)) {
            countryRepository.saveAll(
                    List.of(
                            new CountryEntity("USA"),
                            new CountryEntity("MEXICO"),
                            new CountryEntity("CANADA")

                    )
            );
        }
    }

    private final FuelTypeRepository fuelTypeRepository;

    private void initFuelTypes() {
        if (fuelTypeRepository.existsById(1)) {
            fuelTypeRepository.saveAll(
                    List.of(
                            new FuelTypeEntity("Diesel"),
                            new FuelTypeEntity("Biodiesel"),
                            new FuelTypeEntity("Electricity"),
                            new FuelTypeEntity("Compressed Natural Gas"),
                            new FuelTypeEntity("Liqued Natural Gas"),
                            new FuelTypeEntity("Propane"),
                            new FuelTypeEntity("Ethanol"),
                            new FuelTypeEntity("Methanol"),
                            new FuelTypeEntity("Hydrogen"),
                            new FuelTypeEntity("Gasohol"),
                            new FuelTypeEntity("Gasoline"),
                            new FuelTypeEntity("A55"),
                            new FuelTypeEntity("E85"),
                            new FuelTypeEntity("M85"),
                            new FuelTypeEntity("Van")
                    )
            );
        }
    }

    private final StateRepository statesRepository;

    private void initStates() {
        if (!statesRepository.existsById(1)) {
            statesRepository.saveAll(
                    List.of(
                            new StateEntity("Alabama, AL", new CountryEntity("USA")),
                            new StateEntity("Alaska, AK", new CountryEntity("USA")),
                            new StateEntity("Arizona, AZ", new CountryEntity("USA")),
                            new StateEntity("Arkansas, AR", new CountryEntity("USA")),
                            new StateEntity("California, CA", new CountryEntity("USA")),
                            new StateEntity("Colorado, CO", new CountryEntity("USA")),
                            new StateEntity("Connecticut, CT", new CountryEntity("USA")),
                            new StateEntity("Delaware, DE", new CountryEntity("USA")),
                            new StateEntity("Florida, FL", new CountryEntity("USA")),
                            new StateEntity("Georgia, GA", new CountryEntity("USA")),
                            new StateEntity("Hawaii, HI", new CountryEntity("USA")),
                            new StateEntity("Idaho, ID", new CountryEntity("USA")),
                            new StateEntity("Illinois, IL", new CountryEntity("USA")),
                            new StateEntity("Indiana, IN", new CountryEntity("USA")),
                            new StateEntity("Iowa, IA", new CountryEntity("USA")),
                            new StateEntity("Kansas, KS", new CountryEntity("USA")),
                            new StateEntity("Kentucky, KY", new CountryEntity("USA")),
                            new StateEntity("Louisiana, LA", new CountryEntity("USA")),
                            new StateEntity("Maine, ME", new CountryEntity("USA")),
                            new StateEntity("Maryland, MD", new CountryEntity("USA")),
                            new StateEntity("Massachusetts, MA", new CountryEntity("USA")),
                            new StateEntity("Michigan, MI", new CountryEntity("USA")),
                            new StateEntity("Minnesota, MN", new CountryEntity("USA")),
                            new StateEntity("Mississippi, MS", new CountryEntity("USA")),
                            new StateEntity("Missouri, MO", new CountryEntity("USA")),
                            new StateEntity("Montana, MT", new CountryEntity("USA")),
                            new StateEntity("Nebraska, NE", new CountryEntity("USA")),
                            new StateEntity("Nevada, NV", new CountryEntity("USA")),
                            new StateEntity("New Hampshire, NH", new CountryEntity("USA")),
                            new StateEntity("New Jersey, NJ", new CountryEntity("USA")),
                            new StateEntity("New Mexico, NM", new CountryEntity("USA")),
                            new StateEntity("New York, NY", new CountryEntity("USA")),
                            new StateEntity("North Carolina, NC", new CountryEntity("USA")),
                            new StateEntity("North Dakota, ND", new CountryEntity("USA")),
                            new StateEntity("Ohio, OH", new CountryEntity("USA")),
                            new StateEntity("Oklahoma, OK", new CountryEntity("USA")),
                            new StateEntity("Oregon, OR", new CountryEntity("USA")),
                            new StateEntity("Pennsylvania, PA", new CountryEntity("USA")),
                            new StateEntity("Rhode Island, RI", new CountryEntity("USA")),
                            new StateEntity("South Carolina, SC", new CountryEntity("USA")),
                            new StateEntity("South Dakota, SD", new CountryEntity("USA")),
                            new StateEntity("Tennessee, TN", new CountryEntity("USA")),
                            new StateEntity("Texas, TX", new CountryEntity("USA")),
                            new StateEntity("Utah, UT", new CountryEntity("USA")),
                            new StateEntity("Vermont, VT", new CountryEntity("USA")),
                            new StateEntity("Virginia, VA", new CountryEntity("USA")),
                            new StateEntity("Washington, WA", new CountryEntity("USA")),
                            new StateEntity("West Virginia, WV", new CountryEntity("USA")),
                            new StateEntity("Wisconsin, WI", new CountryEntity("USA")),
                            new StateEntity("Wyoming, WY", new CountryEntity("USA"))
                    )
            );
        }
    }
}
