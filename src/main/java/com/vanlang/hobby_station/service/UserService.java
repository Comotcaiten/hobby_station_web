    package com.vanlang.hobby_station.service;

    import com.vanlang.hobby_station.Role;
    import com.vanlang.hobby_station.model.User;
    import com.vanlang.hobby_station.repository.IRoleRepository;
    import com.vanlang.hobby_station.repository.IUserRepository;
    import jakarta.validation.constraints.NotNull;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;
    import java.util.Optional;

    @Service
    @Slf4j
    @Transactional
    public class UserService implements UserDetailsService {
        @Autowired
        private IUserRepository userRepository;
        @Autowired
        private IRoleRepository roleRepository; // Lưu người dùng mới vào cơ sở dữ liệu sau khi mã hóa mật khẩu.

        public void save(@NotNull User user) {
            // user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            // userRepository.save(user);
            if (user.getId() != null) { // Nếu ID đã tồn tại, chỉ cập nhật thông tin khác
                User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
                user.setPassword(existingUser.getPassword()); // Giữ nguyên mật khẩu cũ
            } else { // Nếu ID không tồn tại, mã hóa mật khẩu mới
                if (user.getPassword() != null) {
                    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                }
            }
            userRepository.save(user);
        }

        public List<User> getAllUser(){
            return userRepository.findAll();
        }

        public long size() {
            return userRepository.findAll().size();
        }

        public User addUser(User user) {
            return userRepository.save(user);
        }


        // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
        public void setDefaultRole(String username) {
            userRepository.findByUsername(username).ifPresentOrElse(user -> {
                user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                userRepository.save(user);
            }, () -> {
                throw new UsernameNotFoundException("User not found");
            });
        } // Tải thông tin chi tiết người dùng để xác thực.

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(user.getAuthorities()).accountExpired(!user.isAccountNonExpired()).accountLocked(!user.isAccountNonLocked()).credentialsExpired(!user.isCredentialsNonExpired()).disabled(!user.isEnabled()).build();
        }

        // Trong UserService
        public Optional<User> findById(Long id) {
            return userRepository.findById(id);
        }

        public void deleteUser(Long id) {
            User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
            // Xóa người dùng, các đơn hàng liên quan sẽ được xóa tự động nhờ cascade
            userRepository.deleteById(id);
        }
        

        // Tìm kiếm người dùng dựa trên tên đăng nhập.
        public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByUsername(username);
        }

        // Tìm kiếm người dùng dựa trên tên đăng nhập.
        public Optional<User> findByEmail(String email) throws UsernameNotFoundException {
            return userRepository.findByEmail(email);
        }

        // Tìm kiếm người dùng dựa trên tên đăng nhập.
        public Optional<User> findByPhone(String phone) throws UsernameNotFoundException {
            return userRepository.findByPhone(phone);
        }

        public boolean usernameExists(String username) {
            return findByUsername(username).isPresent();
        }

        public boolean emailExists(String email) {
            return findByEmail(email).isPresent();
        }

        public boolean phoneExists(String phone) {
            return findByPhone(phone).isPresent();
        }

        // Lấy id
        // Trả về ID của người dùng
        public Long getIdByUsername(String username) {
            return userRepository.findByUsername(username)
                                .map(User::getId)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

    }
