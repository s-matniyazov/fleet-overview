package fleetoverview.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import fleetoverview.domain.entity.RoleEntity;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.repository.RoleRepository;
import fleetoverview.repository.UserRepository;

import java.util.HashSet;
import java.util.List;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 11 февр. 2025
 **/
@Component
public class InitData implements CommandLineRunner {

    public InitData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        initRoles();
        initUsers();
    }

    private final RoleRepository roleRepository;
    private void initRoles() {
        if (!roleRepository.existsByName("ADMIN")) {
            roleRepository.saveAll(
                    List.of(
                            new RoleEntity(
                                    "ADMIN",
                                    "admin",
                                    new HashSet<>()
                            ),
                            new RoleEntity(
                                    "USER",
                                    "user",
                                    new HashSet<>()
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
}
