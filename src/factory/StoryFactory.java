/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package factory;

import javax.mail.NoSuchProviderException;
import javax.mail.Store;

/**
 * 类StoryFactory.java的实现描述：store工厂
 *
 * @author baoxing.peng 2016年12月15日 13:48:06
 */
public interface StoryFactory {

    public Store getInstance() throws NoSuchProviderException;
}
