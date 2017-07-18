package com.e451.rest.services;

import com.e451.rest.domains.auth.FailedLoginAttempt;
import com.e451.rest.repository.FailedLoginRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

/**
 * Created by l659598 on 7/18/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FailedLoginServiceImplTest {

    @Mock
    private FailedLoginRepository failedLoginRepository;

    private List<FailedLoginAttempt> attempts;

    @Before
    public void setup() {

    }


}
