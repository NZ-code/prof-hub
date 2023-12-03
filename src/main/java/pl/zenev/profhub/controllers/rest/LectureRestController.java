package pl.zenev.profhub.controllers.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.zenev.profhub.controllers.api.LectureController;
import pl.zenev.profhub.dto.*;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.helpers.LectureToResponse;
import pl.zenev.profhub.helpers.LecturesToResponse;
import pl.zenev.profhub.helpers.PatchRequestToLecture;
import pl.zenev.profhub.helpers.RequestToLecture;
import pl.zenev.profhub.security.UserRoles;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.LectureService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("")
@Log

public class LectureRestController implements LectureController {
    LectureService lectureService;
    final LecturesToResponse lecturesToResponse;
    final LectureToResponse lectureToResponse;
    CourseService courseService;
    final PatchRequestToLecture patchRequestToLecture;
    private  HttpServletResponse response;
    private final UriInfo uriInfo;
    private final RequestToLecture requestToLecture;
    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    @Inject
    public LectureRestController( LecturesToResponse lecturesToResponse, LectureToResponse lectureToResponse, PatchRequestToLecture patchRequestToLecture, UriInfo uriInfo, RequestToLecture requestToLecture) {
        this.lecturesToResponse = lecturesToResponse;
        this.lectureToResponse = lectureToResponse;
        this.patchRequestToLecture = patchRequestToLecture;
        this.uriInfo = uriInfo;
        this.requestToLecture = requestToLecture;
    }
    @EJB
    public void setLectureService(LectureService service) {
        this.lectureService = service;
    }
    @EJB
    public void setCourseService(CourseService service) {
        this.courseService = service;
    }


    @Override
    public GetLecturesResponse getLectures() {
        return Optional.ofNullable(lectureService.getAll())
                .map(lecturesToResponse)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetLecturesResponse getCourseLectures(UUID id) {
        System.out.println("controller");
        return lectureService.getAllByCourseId(id)
                .map(lecturesToResponse)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetLectureResponse getCourseLecture(UUID courseId, UUID lectureId) {
        return lectureService.getAllByCourseId(courseId)
                .map(lectures -> lectures.stream().filter(lecture -> lecture.getUuid().equals(lectureId)).findFirst()
                        .map(lectureToResponse)
                        .orElseThrow(NotFoundException::new)
                )
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putLecture(UUID courseId, UUID lectureId, PutLectureRequest request) {
        try {
            courseService.getById(courseId).ifPresentOrElse(
                    course ->{
                        Lecture lecture = requestToLecture.apply(lectureId, request);
                        lecture.setCourse(course);
                        lectureService.add(lecture);
                        List<Lecture> lectureList = course.getLectures();
                        lectureList.add(lecture);
                        course.setLectures(lectureList);
                        courseService.update(course);
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(LectureController.class, "getCourseLecture")
                    .build(courseId, lectureId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchLecture(UUID courseId, UUID lectureId, PatchLectureRequest request) {
        try {
            courseService.getById(courseId).ifPresentOrElse(
                    course -> {
                        Optional<Lecture> lectureOpt  = lectureService.getById(lectureId);
                        if(lectureOpt.isPresent()){
                            Lecture lecture = lectureOpt.get();
                            patchRequestToLecture.apply(lecture, request);
                            lecture.setCourse(course);
                            lectureService.update(lecture);
                        }
                        else{
                            throw new NotFoundException();

                        }
                    }
                    ,() -> {
                                            throw new NotFoundException();
                                        }
            );
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(LectureController.class, "getCourseLecture")
                    .build(courseId, lectureId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteLecture(UUID courseId, UUID lectureId) {

        courseService.getById(courseId).ifPresentOrElse(
                entity -> lectureService.delete(lectureId),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
