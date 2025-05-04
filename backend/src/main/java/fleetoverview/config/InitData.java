package fleetoverview.config;

import fleetoverview.domain.entity.ActionEntity;
import fleetoverview.domain.entity.OwnershipTypeEntity;
import fleetoverview.domain.entity.enums.ActionTypesEnum;
import fleetoverview.repository.ActionRepository;
import fleetoverview.repository.OwnershipTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import fleetoverview.domain.entity.RoleEntity;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.repository.RoleRepository;
import fleetoverview.repository.UserRepository;

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

    public InitData(ActionRepository actionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, OwnershipTypeRepository ownershipTypeRepository) {
        this.actionRepository = actionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ownershipTypeRepository = ownershipTypeRepository;
    }

    @Override
    public void run(String... args) {
        initActions();
        initRoles();
        initUsers();
        initOwnershipTypes();
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
}
