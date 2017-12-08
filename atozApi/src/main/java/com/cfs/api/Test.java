package com.cfs.api;

import com.cfs.core.entity.Users;
import com.cfs.core.objects.LoginDTO;
import com.cfs.core.objects.LoginResponse;
import com.cfs.core.objects.OtpResponse;
import com.cfs.core.objects.UserDTO;
import com.cfs.service.IOtpService;
import com.cfs.service.ISMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Queue;

/**
 * @author chopra
 * 12/11/17
 */
@RestController
public class Test {
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }
}
