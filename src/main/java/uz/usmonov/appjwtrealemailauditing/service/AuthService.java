package uz.usmonov.appjwtrealemailauditing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.usmonov.appjwtrealemailauditing.entity.User;
import uz.usmonov.appjwtrealemailauditing.entity.enums.RoleName;
import uz.usmonov.appjwtrealemailauditing.payload.ApiResponse;
import uz.usmonov.appjwtrealemailauditing.payload.LoginDto;
import uz.usmonov.appjwtrealemailauditing.payload.RegisterDto;
import uz.usmonov.appjwtrealemailauditing.repository.RoleRepository;
import uz.usmonov.appjwtrealemailauditing.repository.UserRepository;
import uz.usmonov.appjwtrealemailauditing.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerUser(RegisterDto registerDto) {

        boolean email = userRepository.existsUserByEmail ( registerDto.getEmail () );
        if ( email )
            return new ApiResponse ( "Email already exist", false );

        User user = new User ();
        user.setFirstName ( registerDto.getFirstName () );
        user.setLastName ( registerDto.getLastName () );
        user.setEmail ( registerDto.getEmail () );
        user.setPassword ( passwordEncoder.encode ( registerDto.getPassword () ) );
        user.setRoles ( Collections.singleton ( roleRepository.findByRoleName ( RoleName.ROLE_USER ) ) );
        user.setEmailCode ( UUID.randomUUID ().toString () );
        User save = userRepository.save ( user );

        /*To send a message to Email Address*/
        sendingEmail ( user.getEmail (), user.getEmailCode () );
        return new ApiResponse ( "Successfully Registered!!.Please, confirm the code in your email", true );
    }

    public boolean sendingEmail(String email, String emailCode) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage ();
            mailMessage.setFrom ( "HP.com" );
            mailMessage.setTo ( email );
            mailMessage.setSubject ( "Tasdiqlash" );
            mailMessage.setText ( "http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + email );
            javaMailSender.send ( mailMessage );
            return true;
        } catch (Exception e) {
            return false;

        }

    }

    public ApiResponse verifyEmail(String emailCode,String email){
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode ( email, emailCode );
        if ( optionalUser.isPresent () ){
            User user = optionalUser.get ();
            user.setEnabled ( true );
            user.setEmailCode ( null );
            userRepository.save ( user );
            return new ApiResponse ( "Successfully Verified",true );
        }
        return new ApiResponse ( "Already Verified",false );
    }

    public  ApiResponse login(LoginDto loginDto) {
        try {

            Authentication authenticate = authenticationManager.authenticate ( new UsernamePasswordAuthenticationToken (
                    loginDto.getUsername (),
                    loginDto.getPassword ()
            ) );
            User user = (User) authenticate.getPrincipal ();
            String token = jwtProvider.generateToken ( user.getUsername (), user.getRoles () );
            return new ApiResponse ( "Welcome to your cabinet",true,token );
        }catch (BadCredentialsException badCredentialsException){
            return new ApiResponse ( "Username or Password incorrect",false );
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail ( username ).orElseThrow (()->new UsernameNotFoundException ( username+"not found" )  );
    }
}