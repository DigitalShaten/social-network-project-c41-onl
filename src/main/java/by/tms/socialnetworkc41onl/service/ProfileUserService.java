package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.constant.Gender;
import by.tms.socialnetworkc41onl.dao.FileDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.dao.UserPhotoDao;
import by.tms.socialnetworkc41onl.dto.PostDTO;
import by.tms.socialnetworkc41onl.dto.ProfileDto;
import by.tms.socialnetworkc41onl.model.File;
import by.tms.socialnetworkc41onl.model.FileData;
import by.tms.socialnetworkc41onl.model.User;

/**
 * @author Ирина Мизгир
 * @date 25.07.2026 22:58
 */
public class ProfileUserService {

    private final UserDao userDao = new UserDao();

    private final FileDao fileDao = new FileDao();

    private final UserPhotoDao userPhotoDao = new UserPhotoDao();

    public void editProfile(PostDTO.EditProfileDto editProfileDto) {
        User user = userDao.findById(editProfileDto.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(editProfileDto.firstName());
        user.setLastName(editProfileDto.lastName());
        user.setGender(editProfileDto.gender().toString());
        user.setBirthday(editProfileDto.birthday());
        user.setAbout(editProfileDto.about());
        userDao.update(user);
        FileData photoData = editProfileDto.photoData();
        if (photoData != null) {
            File file = new File();
            file.setFileName(photoData.fileName());
            file.setData(photoData.data());
            File newFile = fileDao.save(file);
            userPhotoDao.updateCurrentUserPhoto(newFile.getId(), user.getId());
        }
    }

    public ProfileDto.UserProfileDto getUserProfileDtoByUserId(long userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Long currentFileId = userPhotoDao.getCurrentPhotoFileIdOrNull(userId);

        return new ProfileDto.UserProfileDto(
                userId,
                user.getFirstName(),
                user.getLastName(),
                Gender.valueOf(user.getGender()),
                user.getBirthday(),
                user.getAbout(),
                currentFileId
        );
    }
}
