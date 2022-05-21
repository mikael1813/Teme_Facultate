#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
# include <GLFW/glfw3.h>
# include <GL/glut.h>
#include <unistd.h>
#include <math.h>
#include <iostream>


float xxx[50][50];
float yyy[50][50];
float zzz[50][50];
float _angleMoon = 0.0;
float angleEarth = 0.0;



void drawSphere(double centerx,double centery,double centerz,double radius, int lats, int longs,double r,double g,double b) {

    int i,j;
    for(i=0;i<=longs;i++){
        float theta = M_PI*i/longs;
        for(j=0;j<=lats;j++){
            float phi = M_PI*2*j/lats;

            float x = centerx + radius * sin(theta) * cos(phi);
            float y = centery + radius * sin(theta) * sin(phi);
            float z = centerz + radius * cos(theta);

            //std::cout<<x<<" "<<y<<" "<<z<<std::endl;

            xxx[i][j] = x;
            yyy[i][j] = y;
            zzz[i][j] = z;
        }
    }
    //std::cout<<"GATATA"<<std::endl;

    glBegin(GL_QUADS);

    for(i=0;i<longs;i++){
        for(j=0;j<lats;j++){
            glColor3f(r, g, b);
            glVertex3f(xxx[i][j],yyy[i][j],zzz[i][j]);
            glColor3f(r, g, b);
            glVertex3f(xxx[i][j+1],yyy[i][j+1],zzz[i][j+1]);
            glColor3f(r, g, b);
            glVertex3f(xxx[i+1][j+1],yyy[i+1][j+1],zzz[i+1][j+1]);
            glColor3f(r, g, b);
            glVertex3f(xxx[i+1][j],yyy[i+1][j],zzz[i+1][j]);
        }
    }


    glEnd();

}


void init(void)
{
   glClearColor (0.0, 0.0, 0.0, 0.0);
   glShadeModel (GL_FLAT);
}

void display(void)
{
   glClear (GL_COLOR_BUFFER_BIT);
   //glColor3f (1.0, 1.0, 1.0);

   //glPushMatrix();
   //glutWireSphere(1.0, 20, 16);   // draw sun
   //glRotatef ((GLfloat) angleEarth, 0.0, 1.0, 0.0);
   //glTranslatef (2.0, 0.0, 0.0);
   //glRotatef ((GLfloat) day, 0.0, 1.0, 0.0);
   //glutWireSphere(0.2, 10, 8);    // draw smaller planet
   //glPopMatrix();
    //glutWireSphere(1.0, 20, 16);
    glPushMatrix();
    //glRotatef(angleEarth,0.0f,1.0f,0.0f);
    drawSphere(0,0,0,2,40,40,1,0.5,0);


    glPushMatrix();
    //glTranslatef(0.1f,0.0f,0.0f);
    glRotatef(angleEarth,0.0f,1.0f,0.0f);
    drawSphere(10,0,0,1,40,40,0,1,1);

    glPushMatrix();

    glPopMatrix();
    //Varianta 1
    glTranslatef(10.0f,0.0f,0.0f);
    glRotatef(_angleMoon,0.0f,1.0f,1.0f);
    drawSphere(2,1,0,0.5,40,40,1,1,1);

    //Varianta 2
    //glTranslatef(10.0f,0.0f,0.0f);
    //glRotatef(_angleMoon,0.0f,1.0f,0.0f);
    //drawSphere(2,0,0,0.5,20,20,1,1,1);

    glPopMatrix();

    glPopMatrix();

    glutSwapBuffers();

}

void reshape (int w, int h)
{
   glViewport (0, 0, (GLsizei) w, (GLsizei) h);
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity ();
   gluPerspective(60.0, (GLfloat) w/(GLfloat) h, 1.0, 100.0);
   glMatrixMode(GL_MODELVIEW);
   glLoadIdentity();
   gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
   glTranslatef(0, 0, -20);
}

void update(int value)
{
    _angleMoon += 0.5;
    if (_angleMoon > 360)
    {
        _angleMoon -= 360;
    }

    angleEarth += 0.1;
    if (angleEarth > 360)
    {
        angleEarth -= 360;
    }

    glutPostRedisplay();
    glutTimerFunc(5,update,0);
}

int main(int argc, char** argv)
{
   glutInit(&argc, argv);
   glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
   glutInitWindowSize (500, 500);
   glutInitWindowPosition (100, 100);
   glutCreateWindow (argv[0]);
   init ();
   glutDisplayFunc(display);
   glutReshapeFunc(reshape);
   glutTimerFunc(25,update,0);
   //glutKeyboardFunc(keyboard);
   glutMainLoop();
   return 0;
}
