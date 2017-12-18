package matmic.librarymanager.repositories;

import matmic.librarymanager.model.rolemodel.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(String role);
}
