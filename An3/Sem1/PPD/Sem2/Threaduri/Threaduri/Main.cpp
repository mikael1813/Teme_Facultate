#include <iostream>
#include <thread>
#include <stdio.h>
#include <tchar.h>
#include <time.h>
#include <vector>
#include <cstdlib>
#include <cmath>


#define MAX 1000000
//se aloca pe heap
//int a[MAX];
//int b[MAX];
//int c[MAX];
using namespace std;

void sum_linear(double a[], double b[], double c[], int start, int end) {
	for (int i = start; i < end; i++) {
		//c[i] = a[i] + b[i];
		c[i] = sqrt(pow(a[i], 4)) + pow(b[i], 4);
	}
}

void sum_ciclic(double a[], double b[], double c[], int thr, int p) {
	for (int i = thr; i < MAX; i = i + p) {
		//c[i] = a[i] + b[i];
		c[i] = sqrt(pow(a[i], 4)) + pow(b[i], 4);
	}
}
void task_linear(double a[], double b[], double c[], int p) {
	vector<thread> t;

	int start = 0, end = 0;
	int chunk = MAX / p;
	int rest = MAX % p;

	for (size_t i = 0; i < p; i++)
	{
		start = end;
		end = start + chunk + (rest-- > 0);
		/*if (rest > 0) {
			end++;
			rest--;
		}*/
		thread thr = thread(sum_linear, a, b, c, start, end);
		t.push_back(move(thr));
	}

	for (size_t i = 0; i < t.size(); i++)
	{
		if (t[i].joinable())
			t[i].join();
	}
}
void task_ciclic(double a[], double b[], double c[], int p) {
	vector<thread> t;
	for (size_t i = 0; i < p; i++)
	{
		thread thr = thread(sum_ciclic, a, b, c, i, p);
		t.push_back(move(thr));
	}

	for (size_t i = 0; i < t.size(); i++)
	{
		if (t[i].joinable())
			t[i].join();
	}
}

int main(int argc, char** argv) {

	//se aloca pe stiva
	double a[MAX];
	double b[MAX];
	double c[MAX];
	srand(time(NULL));
	for (int i = 0; i < MAX; i++)
	{
		/*a[i] = i;
		b[i] = MAX - i;*/
		a[i] = rand() % 100;
		b[i] = rand() % 100;
	}

	/*int a[] = { 1,2,3,4,5,6,7,8,9,10 };
	int b[] = { 1,2,3,4,5,6,7,8,9,10 };
	int c[MAX];*/

	for (int i = 0; i < MAX; i++) {
		//c[i] = a[i] + b[i];
	}

	//sum_linear(a, b, c);
	int p = atoi(argv[1]);
	cout << "Nr thr: " << p << endl;
	//int p = 4;
	//vector<thread> t;

	/*for (size_t i = 0; i < p; i++)
	{
		thread thr = thread(sum_linear, a, b, c);
		t.push_back(move(thr));
	}*/

	/*for (size_t i = 0; i < p; i++)
	{
		thread thr = thread(sum_ciclic, a, b, c, i, p);
		t.push_back(move(thr));
	}

	for (size_t i = 0; i < t.size(); i++)
	{
		if (t[i].joinable())
			t[i].join();
	}*/


	//thread thr(sum_linear, a, b, c);
	//thr.join();

	clock_t startTimp_ciclic = clock();

	task_ciclic(a, b, c, p);

	clock_t stopTimp_ciclic = clock();

	clock_t startTimp_linear = clock();

	task_linear(a, b, c, p);

	clock_t stopTimp_linear = clock();
	/*for (int i = 0; i < MAX; i++) {
		cout << c[i] << " ";
	}*/
	cout << endl << "Timp ciclic: ";
	cout << (double)(stopTimp_ciclic - startTimp_ciclic) / CLOCKS_PER_SEC << endl;

	cout << endl << "Timp linear: ";
	cout << (double)(stopTimp_linear - startTimp_linear) / CLOCKS_PER_SEC << endl;

	return 0;
}