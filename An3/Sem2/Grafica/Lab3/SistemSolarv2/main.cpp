# include <GLFW/glfw3.h>
# include <GL/glut.h>
#include <unistd.h>
#include <math.h>
#include <iostream>


float xxx[50][50];
float yyy[50][50];
float zzz[50][50];

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

float _angleMoon = 0.0;
float angleEarth = 0.0;

void update(int value)
{
    _angleMoon += 3;
    if (_angleMoon > 360)
    {
        _angleMoon -= 360;
    }

    angleEarth += 1;
    if (angleEarth > 360)
    {
        angleEarth -= 360;
    }

    glutPostRedisplay();
    glutTimerFunc(5,update,0);
}


float verticies[8][3] = {
    { 1.0, -1.0, -1.0},
    { 1.0,  1.0, -1.0},
    {-1.0,  1.0, -1.0},
    {-1.0, -1.0, -1.0},
    { 1.0, -1.0,  1.0},
    { 1.0,  1.0,  1.0},
    {-1.0, -1.0,  1.0},
    {-1.0,  1.0,  1.0}
    };

int edges[12][2] = {
    {0, 1},
    {0, 3},
    {0, 4},
    {2, 1},
    {2, 3},
    {2, 7},
    {6, 3},
    {6, 4},
    {6, 7},
    {5, 1},
    {5, 4},
    {5, 7}
    };

int surfaces[6][4] = {
    {0, 1, 2, 3},
    {3, 2, 7, 6},
    {6, 7, 5, 4},
    {4, 5, 1, 0},
    {1, 5, 7, 2},
    {4, 0, 3, 6}
    };


float colors[12][3] = {
    {1.0, 0.0, 0.0},
    {0.0, 1.0, 0.0},
    {0.0, 0.0, 1.0},
    {0.0, 1.0, 0.0},
    {1.0, 1.0, 1.0},
    {0.0, 1.0, 1.0},
    {1.0, 0.0, 0.0},
    {0.0, 1.0, 0.0},
    {0.0, 0.0, 1.0},
    {1.0, 0.0, 0.0},
    {1.0, 1.0, 1.0},
    {0.0, 1.0, 1.0},
    };

int display[2]={400, 400};


void init ( void ){

    // openGL va ascunde suprafetele ce vor fi specificate
    glEnable(GL_CULL_FACE);
    // specificam sa se ascunda pe cele din spate
    glCullFace(GL_BACK);
    // precizam ordinea in care sunt desenate (invers acelor de ceasornic e default) --
    glFrontFace(GL_CW);

    // precizam ca aspura carei matrici vom stabili parametrii (in acest caz asupra matricii de proiectie)
    glMatrixMode( GL_PROJECTION );

    // stabilim perspectiva
    gluPerspective(90, (display[0]/display[1]), 0.0, 50.0);
    // culoarea fundalului
    glClearColor (0.0 ,0.0 ,0.0 ,0.0) ;
    // mutam camera in spate cu 10 unitati ca sa vedem mai bine cubul
    glTranslatef(0, 0, -15);
    //glRotatef(60, 0, 1, 1);
}


void cubeFull( void ){
    int indexSurface, indexVertex;

    glBegin(GL_QUADS);

    for(indexSurface = 0; indexSurface < 6; indexSurface++)
        for(indexVertex = 0; indexVertex < 4; indexVertex++){
                glColor3f(colors[surfaces[indexSurface][indexVertex]][0],colors[surfaces[indexSurface][indexVertex]][1],colors[surfaces[indexSurface][indexVertex]][2]);
                glVertex3f(verticies[surfaces[indexSurface][indexVertex]][0],verticies[surfaces[indexSurface][indexVertex]][1],verticies[surfaces[indexSurface][indexVertex]][2]);
        }
    glEnd();


    glFlush ();
}


 int main ( void )
 {
    GLFWwindow * window ;
    /* Initializam libraria */
    if (! glfwInit ())
        return -1;

    /* Cream o fereastra si ii atasam un context OpenGL */
    window = glfwCreateWindow (display[0], display[1] , "Cube fixed pipeline!", NULL , NULL );
    if (! window )
    {
        glfwTerminate ();
        return -1;
    }

    /* Facem fereastra curenta contextul curent */
    glfwMakeContextCurrent ( window );

    /* se initializeaza conditiile initiale, projection mode, etc. */
    init();

    float angle = 0.0f;
    /* Loop pana cand se inchide fereastra */
    while (! glfwWindowShouldClose ( window ))
    {

        /* Aici se desenează */
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

        std::cout<<angle<<std::endl;
        //glPushMatrix();
        //cubeFull();
        glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
        //glutTimerFunc(25,update,0);

        glPushMatrix();
        drawSphere(0,0,0,2,40,40,1,0.5,0);

        glRotatef(angle, 0.0, 1.0, 0.0); //This will self-rotate the sun

        glPopMatrix();

        //glPushMatrix();

        drawSphere(10,0,0,1,40,40,0,1,1);
        //glRotatef(angleEarth, 1.0,0.0, 0.0);

        drawSphere(12,0,0,0.5,20,20,1,1,1);

        //glPopMatrix();


        angle+=10.0f;
        //drawSphere(10,0,0,1,40,40,0,1,1);

        //glMatrixMode( GL_MODELVIEW );
        //glRotatef(10, 1, 1, 1);
        //glRotatef(10.0, 0, 0, 1);

        //glRotatef(10, 0.0f, 1.0f, 0.0f);
        /* Orbital radius of 3.0 units */
        //glPushMatrix();
        //glTranslatef(1.0f, 0.0f, 0.0f);
        //glRotatef(5, 0.0f, 1.0f, 0.0f);
        //glRotatef(10, 1.0f, 0.0f, 0.0f);


        //glPopMatrix();

        /* Se inverseaza bufferele */
        glfwSwapBuffers ( window );

        /* intarziem putin ca sa putem sa vedem rotatia */
        usleep(100000);

        /* Procesam evenimentelele */
        glfwPollEvents ();

    }

    glfwTerminate ();
    return 0;
 }
