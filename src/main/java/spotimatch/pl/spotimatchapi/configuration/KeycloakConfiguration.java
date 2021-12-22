package spotimatch.pl.spotimatchapi.configuration;

//
//@org.keycloak.adapters.springsecurity.KeycloakConfiguration
//public class KeycloakConfiguration extends KeycloakWebSecurityConfigurerAdapter {
//    @Autowired
//    public void configureGlobal(
//            AuthenticationManagerBuilder auth) throws Exception {
//
//        KeycloakAuthenticationProvider keycloakAuthenticationProvider
//                = keycloakAuthenticationProvider();
//        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(
//                new SimpleAuthorityMapper());
//        auth.authenticationProvider(keycloakAuthenticationProvider);
//    }
//
//    @Bean
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(
//                new SessionRegistryImpl());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/test*")
//                .hasRole("user")
//                .anyRequest()
//                .permitAll();
//    }
//}
