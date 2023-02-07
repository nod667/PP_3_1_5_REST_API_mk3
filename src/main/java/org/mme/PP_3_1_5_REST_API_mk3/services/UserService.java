package org.mme.PP_3_1_5_REST_API_mk3.services;

import jakarta.annotation.PostConstruct;
import org.mme.PP_3_1_5_REST_API_mk3.models.Role;
import org.mme.PP_3_1_5_REST_API_mk3.models.User;
import org.mme.PP_3_1_5_REST_API_mk3.repositories.RoleRepository;
import org.mme.PP_3_1_5_REST_API_mk3.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findById(Long id){
        return userRepository.getReferenceById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public void saveOrUpdateUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    public User findUserByUserName(String userName) {
       return findAll().stream().filter(user -> user.getUsername().equals(userName)).findAny().orElse(null);
    }

    @PostConstruct
    private void postConstruct() {
        Role admin = new Role(1L, "ROLE_ADMIN");
        Role user = new Role(2L, "ROLE_USER");
        roleRepository.saveAll(List.of(admin, user));

        User adminUser = new User();
        adminUser.setName("Maksim");
        adminUser.setLastName("Malakhov");
        adminUser.setAge((byte) 45);
        adminUser.setLogin("admin");
        adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));
        adminUser.addRole(admin);

        User normalUser = new User();
        normalUser.setName("Pirozhok");
        normalUser.setLastName("Malakhova");
        normalUser.setAge((byte) 4);
        normalUser.setLogin("user");
        normalUser.setPassword(bCryptPasswordEncoder.encode("user"));
        normalUser.addRole(user);

        userRepository.save(adminUser);
        userRepository.save(normalUser);
    }
}