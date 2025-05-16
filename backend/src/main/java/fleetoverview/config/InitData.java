package fleetoverview.config;

import fleetoverview.domain.entity.*;
import fleetoverview.domain.enums.ActionTypesEnum;
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

    private final ActionRepository actionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OwnershipTypeRepository ownershipTypeRepository;
    private final CountryRepository countryRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final StateRepository stateRepository;
    private Set<ActionEntity> actions = new HashSet<>();

    public InitData(ActionRepository actionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, OwnershipTypeRepository ownershipTypeRepository, StateRepository stateRepository, FuelTypeRepository fuelRepository, CountryRepository countryRepository, CountryRepository countryRepository1) {
        this.actionRepository = actionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.stateRepository = stateRepository;
        this.fuelTypeRepository = fuelRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) {
        initActions();
        initRoles();
        initUsers();
        initOwnershipTypes();
        initFuelTypes();
        initCountryAndStates();
    }

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

    private void initOwnershipTypes() {
        if (!ownershipTypeRepository.existsByName("Company")) {
            ownershipTypeRepository.saveAll(
                    List.of(
                            new OwnershipTypeEntity("Company"),
                            new OwnershipTypeEntity("Owner Operator's"),
                            new OwnershipTypeEntity("Contractor Owned")
                    )
            );
        }
    }

    private void initFuelTypes() {
        if (!fuelTypeRepository.existsById(1)) {
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

    private void initCountryAndStates() {
        if (!countryRepository.existsById(1)) {
            CountryEntity usa = countryRepository.save(new CountryEntity("USA"));
            CountryEntity mexico = countryRepository.save(new CountryEntity("MEXICO"));
            CountryEntity canada = countryRepository.save(new CountryEntity("CANADA"));

            stateRepository.saveAll(
                    List.of(
                            new StateEntity("Alabama, AL", usa),
                            new StateEntity("Alaska, AK", usa),
                            new StateEntity("Arizona, AZ", usa),
                            new StateEntity("Arkansas, AR", usa),
                            new StateEntity("California, CA", usa),
                            new StateEntity("Colorado, CO", usa),
                            new StateEntity("Connecticut, CT", usa),
                            new StateEntity("Delaware, DE", usa),
                            new StateEntity("Florida, FL", usa),
                            new StateEntity("Georgia, GA", usa),
                            new StateEntity("Hawaii, HI", usa),
                            new StateEntity("Idaho, ID", usa),
                            new StateEntity("Illinois, IL", usa),
                            new StateEntity("Indiana, IN", usa),
                            new StateEntity("Iowa, IA", usa),
                            new StateEntity("Kansas, KS", usa),
                            new StateEntity("Kentucky, KY", usa),
                            new StateEntity("Louisiana, LA", usa),
                            new StateEntity("Maine, ME", usa),
                            new StateEntity("Maryland, MD", usa),
                            new StateEntity("Massachusetts, MA", usa),
                            new StateEntity("Michigan, MI", usa),
                            new StateEntity("Minnesota, MN", usa),
                            new StateEntity("Mississippi, MS", usa),
                            new StateEntity("Missouri, MO", usa),
                            new StateEntity("Montana, MT", usa),
                            new StateEntity("Nebraska, NE", usa),
                            new StateEntity("Nevada, NV", usa),
                            new StateEntity("New Hampshire, NH", usa),
                            new StateEntity("New Jersey, NJ", usa),
                            new StateEntity("New Mexico, NM", usa),
                            new StateEntity("New York, NY", usa),
                            new StateEntity("North Carolina, NC", usa),
                            new StateEntity("North Dakota, ND", usa),
                            new StateEntity("Ohio, OH", usa),
                            new StateEntity("Oklahoma, OK", usa),
                            new StateEntity("Oregon, OR", usa),
                            new StateEntity("Pennsylvania, PA", usa),
                            new StateEntity("Rhode Island, RI", usa),
                            new StateEntity("South Carolina, SC", usa),
                            new StateEntity("South Dakota, SD", usa),
                            new StateEntity("Tennessee, TN", usa),
                            new StateEntity("Texas, TX", usa),
                            new StateEntity("Utah, UT", usa),
                            new StateEntity("Vermont, VT", usa),
                            new StateEntity("Virginia, VA", usa),
                            new StateEntity("Washington, WA", usa),
                            new StateEntity("West Virginia, WV", usa),
                            new StateEntity("Wisconsin, WI", usa),
                            new StateEntity("Wyoming, WY", usa)
                    )
            );
        }
    }
}
