package com.ridvancopur.web.rest;

import com.ridvancopur.domain.Department;
import com.ridvancopur.repository.DepartmentRepository;
import com.ridvancopur.web.rest.errors.BadRequestAlertException;
import com.ridvancopur.web.rest.util.HeaderUtil;
import com.ridvancopur.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    private final DepartmentRepository departmentRepository;

    public DepartmentResource(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * POST  /departments : Create a new department.
     *
     * @param department the department to create
     * @return the ResponseEntity with status 201 (Created) and with body the new department, or with status 400 (Bad Request) if the department has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to save Department : {}", department);
        if (department.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Department result = departmentRepository.save(department);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /departments : Updates an existing department.
     *
     * @param department the department to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated department,
     * or with status 400 (Bad Request) if the department is not valid,
     * or with status 500 (Internal Server Error) if the department couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departments")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to update Department : {}", department);
        if (department.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Department result = departmentRepository.save(department);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, department.getId().toString()))
                .body(result);
    }

    /**
     * GET  /departments : get all the departments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of departments in body
     */
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments(Pageable pageable) {
        log.debug("REST request to get a page of Departments");
        Page<Department> page = departmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /departments/:id : get the "id" department.
     *
     * @param id the id of the department to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the department, or with status 404 (Not Found)
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Optional<Department> department = departmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(department);
    }

    /**
     * DELETE  /departments/:id : delete the "id" department.
     *
     * @param id the id of the department to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);

        departmentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
