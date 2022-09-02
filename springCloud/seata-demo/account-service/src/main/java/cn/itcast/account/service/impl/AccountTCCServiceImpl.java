package cn.itcast.account.service.impl;

import cn.itcast.account.entity.AccountFreeze;
import cn.itcast.account.mapper.AccountFreezeMapper;
import cn.itcast.account.mapper.AccountMapper;
import cn.itcast.account.service.AccountTCCService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountTCCServiceImpl implements AccountTCCService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFreezeMapper freezeMapper;
    @Transactional
    @Override
    public void deduct(String userId, int money) {
        //try
        AccountFreeze oldFreeze = freezeMapper.selectById(RootContext.getXID());
        if(oldFreeze!=null){
            //cancel执行过，拒绝业务
            return;
        }
        accountMapper.deduct(userId, money);
        AccountFreeze freeze = new AccountFreeze();
        freeze.setFreezeMoney(money);
        freeze.setState(AccountFreeze.State.TRY);
        freeze.setUserId(userId);
        freeze.setXid(RootContext.getXID());
        freezeMapper.insert(freeze);
    }

    //第二阶段
    @Override
    public boolean confirm(BusinessActionContext ctx) {
        //第一阶段成功直接删除，冻结记录
        int count = freezeMapper.deleteById(ctx.getXid());
        return count==1;
    }

    @Override
    public boolean cancel(BusinessActionContext ctx) {
        //第一阶段失败，根据冻结的记录会滚
        AccountFreeze accountFreeze = freezeMapper.selectById(ctx.getXid());
        if(accountFreeze==null){
            //没有进行过try，空回滚
            AccountFreeze newFrezz = new AccountFreeze();
            newFrezz.setState(AccountFreeze.State.CANCEL);
            newFrezz.setXid(ctx.getXid());
            newFrezz.setUserId(ctx.getActionContext("userId").toString());
            freezeMapper.insert(newFrezz);
            return true;
        }
        //幂等
        if(accountFreeze.getState()==AccountFreeze.State.CANCEL){
            return true;
        }
        String userId = accountFreeze.getUserId();
        Integer money = accountFreeze.getFreezeMoney();
         accountMapper.refund(userId, money);

        //将冻结的金额清零，修改状态为CANCEL
        accountFreeze.setFreezeMoney(0);
        accountFreeze.setState(AccountFreeze.State.CANCEL);
        int  count = freezeMapper.updateById(accountFreeze);
        return count==1;
    }
}
