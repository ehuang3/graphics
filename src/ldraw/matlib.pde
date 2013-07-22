// CS 3451 Spring 2013 Homework 1 Stub
// Dummy routines for matrix transformations.
// These are for you to write!
import java.util.*;
//import ldraw;

Stack<gtMatrix> matrixStack = new Stack<gtMatrix>();
gtMatrix projection = new gtMatrix();
gtVector lastVertex = null;

void gtInitialize() {
  matrixStack.clear();
  matrixStack.push(gtMatrix.Identity());
  projection = new gtMatrix();
  lastVertex = null;
}

void gtPushMatrix() {
  gtMatrix matrix = matrixStack.peek();
  matrixStack.push(new gtMatrix(matrix));
}

void gtPopMatrix() {
  matrixStack.pop();
}

void gtTranslate(float tx, float ty, float tz) {
  matrixStack.peek().translate(tx, ty, tz);
}

void gtScale(float sx, float sy, float sz) {
  matrixStack.peek().scale(sx, sy, sz);
}

void gtRotate(float angle, float ax, float ay, float az) {
  matrixStack.peek().rotate(angle, ax, ay, az);
}

void gtPerspective(float fovy, float nnear, float ffar) {
  projection = gtMatrix.Frustum(fovy, nnear, ffar);
}

void gtOrtho(float left, float right, float bottom, float top, float nnear, float ffar) {
  projection = gtMatrix.Ortho(left, right, bottom, top, nnear, ffar);
}

void gtBeginShape(int type) { }

void gtEndShape() { }

void gtVertex(float x, float y, float z) {
  if(lastVertex == null) {
    lastVertex = new gtVector(x, y, z);
    lastVertex = matrixStack.peek().mult(lastVertex);
    return;
  }
  
  gtVector currVertex = new gtVector(x, y, z);
  currVertex = matrixStack.peek().mult(currVertex);
  
  lastVertex = projection.mult(lastVertex);
  currVertex = projection.mult(currVertex);
  
  lastVertex.hom_scale(lastVertex.w());
  currVertex.hom_scale(currVertex.w());
  
  xyz p0 = new xyz(lastVertex.x(), lastVertex.y(), lastVertex.z());
  xyz p1 = new xyz(currVertex.x(), currVertex.y(), currVertex.z());
  
  int ok = near_far_clip(-1, 1, p0, p1);
  
  if(ok == 1) {
    float x0 = (p0.x + 1) * width/2;
    float y0 = (p0.y + 1) * height/2;
    float x1 = (p1.x + 1) * width/2;
    float y1 = (p1.y + 1) * height/2;
    draw_line(x0, y0, x1, y1);
  }
  
  lastVertex = null;
}

