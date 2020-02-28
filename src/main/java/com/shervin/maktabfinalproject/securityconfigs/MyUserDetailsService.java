package com.shervin.maktabfinalproject.securityconfigs;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountRepository;
import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
//    @Autowired
//    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(userName);
        account.orElseThrow(() -> new UsernameNotFoundException("not found:" + userName));
        return account.map(MyUserDetails::new).get();
//        return new MyUserDetails(userName);
    }
}
