

syms c1 c2 c3 c4 c5 c6 c7 c8 c9 c10 x1 x2 x3 x4 x5 x6 x7 x8 x9 x10

for i = 1:20
    val = eval(int(x^(i-1)/sqrt(1-x^2), -1, 1));
    eqns = [c1*(x1^(i-1)) + c2*(x2^(i-1)) + c3*(x3^(i-1)) + c4*(x4^(i-1)) + c5*(x5^(i-1)) + c6*(x6^(i-1)) + c7*(x7^(i-1)) + c8*(x8^(i-1)) + c9*(x9^(i-1)) + c10*(x10^(i-1))==val];
    if i == 1
        equations = [eqns];
    else
        equations = [equations,eqns];
    end

end

equations


S = solve(equations,[c1 c2 c3 c4 c5 c6 c7 c8 c9 c10 x1 x2 x3 x4 x5 x6 x7 x8 x9 x10])

S
