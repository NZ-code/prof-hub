package pl.zenev.profhub.controllers.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pl.zenev.profhub.controllers.api.LectureController;
import pl.zenev.profhub.dto.*;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.helpers.LectureToResponse;
import pl.zenev.profhub.helpers.LecturesToResponse;
import pl.zenev.profhub.helpers.PatchRequestToLecture;
import pl.zenev.profhub.helpers.RequestToLecture;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.LectureService;

import java.util.List;
import java.util.UUID;

@Path("")
public class LectureRestController implements LectureController {
    final LectureService lectureService;
    final LecturesToResponse lecturesToResponse;
    final LectureToResponse lectureToResponse;
    final CourseService courseService;
    final PatchRequestToLecture patchRequestToLecture;
    private  HttpServletResponse response;
    private final UriInfo uriInfo;
    private final RequestToLecture requestToLecture;
    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    @Inject
    public LectureRestController(LectureService lectureService, LecturesToResponse lecturesToResponse, LectureToResponse lectureToResponse, CourseService courseService, PatchRequestToLecture patchRequestToLecture, UriInfo uriInfo, RequestToLecture requestToLecture) {
        this.lectureService = lectureService;
        this.lecturesToResponse = lecturesToResponse;
        this.lectureToResponse = lectureToResponse;
        this.courseService = courseService;
        this.patchRequestToLecture = patchRequestToLecture;
        this.uriInfo = uriInfo;
        this.requestToLecture = requestToLecture;
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
                    course ->{
                        Lecture lecture = patchRequestToLecture.apply(lectureId, request);
                        lecture.setCourse(course);
                        lectureService.update(lecture);
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
    public void deleteLecture(UUID courseId, UUID lectureId) {

        courseService.getById(courseId).ifPresentOrElse(
                entity -> lectureService.delete(lectureId),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
