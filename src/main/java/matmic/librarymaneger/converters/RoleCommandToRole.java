//package matmic.librarymaneger.converters;
//
//import lombok.Synchronized;
//import matmic.librarymaneger.commands.RoleCommand;
//import matmic.librarymaneger.model.rolemodel.Role;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RoleCommandToRole implements Converter<RoleCommand, Role> {
//
//    private final EmployeeRoleCommandToEmployeeRole employeeRoleCommandToEmployeeRole;
//
//
//    public RoleCommandToRole(EmployeeRoleCommandToEmployeeRole employeeRoleCommandToEmployeeRole) {
//        this.employeeRoleCommandToEmployeeRole = employeeRoleCommandToEmployeeRole;
//    }
//
//    @Synchronized
//    @Nullable
//    @Override
//    public Role convert(RoleCommand roleCommand) {
//        if(roleCommand == null) {
//            return null;
//        }
//
//        final Role role = new Role();
//        role.setId(roleCommand.getId());
//        role.setName(roleCommand.getName());
//
//        if(roleCommand.getEmployeeRoles() != null && roleCommand.getEmployeeRoles().size() > 0){
//            roleCommand.getEmployeeRoles().forEach( employeeRole -> role.getEmployeeRoles()
//                    .add(employeeRoleCommandToEmployeeRole.convert(employeeRole)));
//        }
//
//
//        return role;
//    }
//}
