package e_wallet.auth_service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("user/wallet")
public class AuthController {
    @GetMapping("create")
    public void create(){

    }
}
