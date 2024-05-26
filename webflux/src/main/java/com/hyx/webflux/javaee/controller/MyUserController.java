package com.hyx.webflux.javaee.controller;

import com.hyx.webflux.javaee.model.MyUser;
import com.hyx.webflux.javaee.service.MyUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author hyxzjbnb
 * @create 2024-05-25-22:35
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("user")
public class MyUserController {

    private final MyUserService myUserService;

    @GetMapping("findAll")
    public Flux<MyUser> findAll(){
        return myUserService.findAll();
    }

    @GetMapping
    public Mono<MyUser> findByUsername(@RequestParam("username") String username){
        return myUserService.findByUsername(username);
    }

    @PostMapping
    public Mono<MyUser> save(@RequestBody MyUser user){
        return myUserService.save(user);
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestParam("id") Long id){
        return myUserService.deleteById(id);
    }

    @GetMapping("/slice")
    public Flux<MyUser> findAllAsSlice() {
        final Pageable pageable = SliceRequest.of(
                0,
                5,
                Sort.by(Sort.Order.by("username").with(Sort.Direction.ASC))
        );
        return myUserService.findAllAsSlice(Query.empty(), pageable);
    }

    @GetMapping("/page")
    public Mono<Page<MyUser>> findAllAsPage() {
        final Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by(Sort.Order.by("username").with(Sort.Direction.ASC))
        );
        return myUserService.findAllAsPage(Query.empty(), pageable);
    }

    public static class SliceRequest implements Pageable {

        private final long offset;

        private final int limit;

        private final Sort sort;

        protected SliceRequest(final long offset, final int limit, final Sort sort) {
            this.offset = offset;
            this.limit = limit;
            this.sort = sort;
        }

        public static SliceRequest of(final long offset, final int limit, final Sort sort) {
            return new SliceRequest(offset, limit, sort);
        }

        public static SliceRequest of(final long offset, final int limit) {
            return of(offset, limit, Sort.unsorted());
        }

        @Override
        public int getPageNumber() {
            return -1;
        }

        @Override
        public int getPageSize() {
            return limit;
        }

        @Override
        public long getOffset() {
            return offset;
        }

        @Override
        public Sort getSort() {
            return sort;
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return null;
        }

        @Override
        public Pageable first() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    }
}
