package com.shervin.maktabfinalproject.securityconfigs;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountRepository;
import com.shervin.maktabfinalproject.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    final AccountRepository accountRepository;

    @Autowired
    public MyUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(userName);
        account.orElseThrow(() -> new UsernameNotFoundException("not found:" + userName));
        return account.map(MyUserDetails::new).get();

    }
}
