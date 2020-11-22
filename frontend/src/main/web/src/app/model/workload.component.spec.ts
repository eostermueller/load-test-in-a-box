import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Workload } from './workload';


describe("can detect base64", function() {
  it("adds integers correctly", function() {
    let workload = new Workload();
    expect( workload.isBase64Ish('abc..!!!') ).toBe(false);
  });
});

