package com.know.info;

import com.know.info.dto.OrgDto;
import com.know.info.dto.UserDto;
import com.know.info.service.ConsumersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KnowledgeNoLimitApplicationTests {
    @Resource
    private ConsumersService consumersService;





    @Test
    public void queryIdList() {
        consumersService.queryIdList(1);


    }

    @Test
    public void postTest() {
        UserDto userDto1=new UserDto();
        userDto1.setUserCode("userDto1");
        userDto1.setUserName("测试1");
        userDto1.setMphone("123");
        userDto1.setEmail("123");
        consumersService.post(userDto1);
    }

    @Test
    public void addBatchUserTest() {
        UserDto userDto1=new UserDto();
        userDto1.setUserCode("userDto3");
        userDto1.setUserName("测试3");
        userDto1.setMphone("456");
        userDto1.setEmail("789");
        UserDto userDto2=new UserDto();
        userDto2.setUserCode("userDto4");
        userDto2.setUserName("测试4");
        userDto2.setMphone("679");
        userDto2.setEmail("890");
        List<UserDto> list = new ArrayList<>();
        list.add(userDto1);
        list.add(userDto2);

        long size=consumersService.addBatchUser(list);
    }

    @Test
    public void questUserList() {
        OrgDto orgDto = new OrgDto();
        orgDto.setOrgId(1);
        UserDto userDto=new UserDto();
        userDto.setOrgDto(orgDto);

        List<UserDto> userDtos=consumersService.queryDtoList(userDto);
        for (UserDto dtos:userDtos){
            System.out.println(dtos.getId());
            System.out.println(dtos.getOrgDto().getCreateTime());
        }
    }

}
