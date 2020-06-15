package service;

import org.springframework.stereotype.Service;

@Service
public class BatchInServiceImpl1 implements BatchInService {
    @Override
    public String invoke() {
        return "1";
    }
}
