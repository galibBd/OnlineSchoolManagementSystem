public class LoginService {
    
    private final AppService appService;
    
    public LoginService(AppService appService) {
        this.appService = appService;
    }
    
    /**
     * Authenticate user and return role-based redirect path
     */
    public String authenticateAndGetRedirectPath(Users users) {
        if (users == null || users.getU_name() == null || users.getPassword() == null) {
            throw new IllegalArgumentException("Username and password required");
        }
        
        List<Users> foundUsers = appService.checkUser(
            users.getU_name(), 
            users.getPassword(), 
            users.getR_id()
        );
        
        if (foundUsers.isEmpty()) {
            return "redirect:user_registration.htm";
        }
        
        return getRedirectPathByRole(users.getR_id());
    }
    
    /**
     * Get redirect path based on role
     */
    private String getRedirectPathByRole(int roleId) {
        switch (roleId) {
            case 1: // Admin
                return "redirect:adminHome.htm";
            case 2: // Teacher
                return "redirect:teacherHome.htm";
            case 3: // Student
                return "redirect:studentHome.htm";
            default:
                return "redirect:user_registration.htm";
        }
    }
}