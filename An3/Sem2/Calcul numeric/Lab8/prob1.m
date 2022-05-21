% cubic_spline - calculeaza coeficientii pentru mai multe tipuri de spline
% apel c = cubic_spline(x, f, type, der)
% x - abscisele 
% f - ordonatele(valorile functiei) 
% type - 0  spline complete
%        1  spline care reproduc derivatele de ordinul al doilea
%        2  spline naturale
%        3  spline deBoor
% der - values valorile derivatelor
%      [f'(a),f'(b)] pentru tipul 0
%      [f''(a), f''(b)] pentru tipul 1
% c - coeficientii (length(x) - 1) (4 matrice, in ordine descrescatoare)
function c = prob1(x, f, type, der)
    % spline natural
    if (nargin<4) || (type==2)
        der = [0,0];
    end
    n = length(x);
    if any(diff(x) < 0)
        [x, ind] = sort(x);
    else
        ind=1:n;
    end
    y = f(ind);
    x = x(:);
    y = y(:);
    % deltax
    dx = diff(x);
    % diferente divizate
    ddiv = diff(y)./ dx;
    % diagonalele
    ds = dx(1:end-1);
    dd = dx(2:end);
    dp = 2 * (ds + dd);                 
    md = 3 * (dd.* ddiv(1:end-1) + ds.* ddiv(2:end));
    switch type
    case 0 %complete
        dp1 = 1; dpn = 1; vd1 = 0; vdn = 0;
        md1 = der(1); mdn = der(2);
    case {1,2}
        dp1 = 2; dpn = 2; vd1 = 1; vdn = 1;
        md1 = 3 * ddiv(1) - 0.5 * dx(1) * der(1);
        mdn=3 * ddiv(end) + 0.5 * dx(end) * der(2);
    case 3 %deBoor
        x31 = x(3) - x(1);
        xn = x(n) - x(n - 2);
        dp1 = dx(2);
        dpn = dx(end - 1);
        vd1 = x31;
        vdn = xn;
        md1 = ((dx(1) + 2 * x31) * dx(2) * ddiv(1) + dx(1)^2 * ddiv(2)) / x31;
        mdn = (dx(end)^2 * ddiv(end - 1) + (2 * xn + dx(end)) * dx(end - 1) * ddiv(end)) / xn;
    end
    % sparse system
    dp = [dp1; dp; dpn];
    dp1 = [0; vd1; dd];
    dm1 = [ds; vdn; 0];
    md = [md1; md; mdn];
    A = spdiags([dm1, dp, dp1], -1:1, n, n);
    m = A \ md;
    c(:,4) = y(1:end-1);
    c(:,3) = m(1:end-1);
    c(:,1) = (m(2:end) + m(1:end-1) - 2 * ddiv)./(dx.^2);
    c(:,2) = (ddiv - m(1:end-1))./ dx - dx.* c(:, 1);
end

