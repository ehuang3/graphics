// CS 3451 Spring 2013 Homework 1 Stub
// Dummy routines for matrix transformations.
// These are for you to write!



void gtInitialize() {
  gtMatrix I = gtMatrix.Identity();
  System.out.println("I = \n" + I);
}

void gtPushMatrix() { }

void gtPopMatrix() { }

void gtTranslate(float tx, float ty, float tz) { }

void gtScale(float sx, float sy, float sz) { }

void gtRotate(float angle, float ax, float ay, float az) { }

void gtPerspective(float fovy, float nnear, float ffar) { }

void gtOrtho(float left, float right, float bottom, float top, float nnear, float ffar) { }

void gtBeginShape(int type) { }

void gtEndShape() { }

void gtVertex(float x, float y, float z) { }

