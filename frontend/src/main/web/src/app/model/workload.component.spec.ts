import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { Workload } from './workload';


describe("base64 testing", function() {
  it("can detect improper base64 encoding", function() {
    let workload = new Workload();
    expect( workload.isBase64Ish_('abc..!!!') ).toBe(false);
  });

  it("can identify real base64", function() {
    let workload = new Workload();
    //Recipe:
    //Start with clear text.
    //Compress it with base64 encoding.
    //Encrypted it with base64 encoding.
    let compressedAndEncrypted:string = 'EwvJ71B+eMqyAouZ04ThYRQuA5hppmobwB3wrN1Tc54L5kUqVN6vFsHlFI95vx1bMilCrsRmgOvV614WVva0TaoV4Rcqnv4+YbhMgCuchHVe/j10sgEqPSePVHXRASivISZaCmNNeRoNZhnqsipU6q17jnlo0f9sde3kDDdQTF8=';
    expect( workload.isBase64Ish_(compressedAndEncrypted) ).toBe(true);
  });

});

