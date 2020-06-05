package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public Account getAccount(String username){
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password){
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public List<Account> getAccounts(){
        return accountMapper.getAccounts();
    }

    public void deleteAccount(Account account){
        accountMapper.deleteAccount(account);
    }
    /*
        声明式事务处理
     */
    @Transactional
    public void insertAccount(Account account){
        accountMapper.insertAccount(account);
//        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account){
        accountMapper.updateAccount(account);
//        accountMapper.updateProfile(account);

        if(account.getPassword() != null && account.getPassword().length() > 0){
            accountMapper.updateSignon(account);
        }
    }
}
