package cn.itcast.server.handler;

import cn.itcast.message.LoginRequestMessage;
import cn.itcast.message.LoginResponseMessage;
import cn.itcast.server.service.UserService;
import cn.itcast.server.service.UserServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        String username = loginRequestMessage.getUsername();
        String password = loginRequestMessage.getPassword();
        UserService userService = UserServiceFactory.getUserService();
        boolean login = userService.login(username, password);
        LoginResponseMessage loginResponseMessage;
        if (login) {
            loginResponseMessage = new LoginResponseMessage();
            loginResponseMessage.setSuccess(true);
            loginResponseMessage.setReason("登录成功");
        } else {
            loginResponseMessage = new LoginResponseMessage();
            loginResponseMessage.setSuccess(false);
            loginResponseMessage.setReason("登录失败");
        }

        channelHandlerContext.writeAndFlush(loginResponseMessage);
    }
}
