package ssh_call.call.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import ssh_call.call.service.CmdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CmdsController {

    private final CmdsService cmdsService;

    @GetMapping("/cmds/commandString")
    @ResponseBody
    public String executeScriptByCommand(String command){
        return cmdsService.executeCmdByStringCommand(command);
    }
}
