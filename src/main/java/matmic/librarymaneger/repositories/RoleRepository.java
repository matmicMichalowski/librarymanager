package matmic.librarymaneger.repositories;

import matmic.librarymaneger.model.rolemodel.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String role);
}
