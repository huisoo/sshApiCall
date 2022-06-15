package ssh_call.call.controller;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CmdsController {

    private final CmdsService cmdsService;

    @GetMapping("/cmds/commandString")
    @ResponseBody
    public Map executeScriptByCommand(String command){
        log.info("execute command start");
        return cmdsService.executeCmdByStringCommand(command);
    }
}
