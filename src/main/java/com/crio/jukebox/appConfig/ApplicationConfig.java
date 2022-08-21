package com.crio.jukebox.appConfig;

import com.crio.jukebox.commands.*;
import com.crio.jukebox.repositories.*;
import com.crio.jukebox.services.*;

public class ApplicationConfig {
    private final IAlbumRepository albumRepository = new AlbumRepository();
    private final ISongRepository songRepository = new SongRepository();
    private final IUserPlayListRepository userPlayListRepository = new UserPlayListRepository();
    private final IUserRepository userRepository = new UserRepository(userPlayListRepository);


    private final IuserService userService = new UserService(userRepository);
    private final ISongService songService = new SongService(songRepository,albumRepository);
    private final IUserPlayListService userPlayListService = new UserPlayListService(userRepository,songRepository,userPlayListRepository);


    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final LoadDataCommand loadDataCommand = new LoadDataCommand(songService);
    private final CreatePlayListCommand createPlayListCommand = new CreatePlayListCommand(userPlayListService);
    private final DeletePlayListCommand deletePlayListCommand = new DeletePlayListCommand(userPlayListService);
    private final ModifyPlayListCommand modifyPlayListCommand = new ModifyPlayListCommand(userPlayListService);
    private final PlayPlayListCommand playPlayListCommand = new PlayPlayListCommand(userPlayListService);
    private final PlaySongOnPlayListCommand playSongOnPlayListCommand = new PlaySongOnPlayListCommand(userPlayListService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("CREATE-USER",createUserCommand);
        commandInvoker.register("LOAD-DATA",loadDataCommand);
        commandInvoker.register("CREATE-PLAYLIST",createPlayListCommand);
        commandInvoker.register("DELETE-PLAYLIST",deletePlayListCommand);
        commandInvoker.register("MODIFY-PLAYLIST",modifyPlayListCommand);
        commandInvoker.register("PLAY-PLAYLIST",playPlayListCommand);
        commandInvoker.register("PLAY-SONG",playSongOnPlayListCommand);
        return commandInvoker;
    }

}
