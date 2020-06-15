package service;

import org.springframework.stereotype.Service;

@Service
public class BatchInServiceImpl2 implements BatchInService {
    @Override
    public String invoke() {
        return "2";
    }
}
