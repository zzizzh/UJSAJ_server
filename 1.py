"""1. 정수, 실수, 문자열을 입력받고 입력 받은 문자열을 출력하시오. (24, 5.32, 'Your_Student_Number')
 
2. 2번을 형식인자를 이용하여 출력하시오.

3. 사칙연산과 %, //를 모두 임의 수를 이용하여 계산하고 출력하시오.
 
4. 여러분의 학번을 문자열로 입력하고 '20XX'의 여러분의 학번의 년도를 2999년으로 변경하시오.
 
5. 'ABCDEF'의 문자열에서 각 문자의 구분기호(':')를 입력하여 출력하고, 이를 다시 각 문자로 구분하여 출력하시오."""

z='   abcdefghijk  '
print(z)
print(z.count('j'))
z=z.strip()
print(z)
print(z.find('d'))
z=z.upper()
print(z)
z=z.lower()
print(z)


x=int(input("x:"))
y=float(input("y:"))
z=input("z:")

print(x,y,z)
print("%d %f %s"%(x,y,z))

x=int(input("x:"))
y=int(input("y:"))

print("x+y=%d"%(x+y))
print("x-y=%d"%(x-y))
print("x*y=%d"%(x*y))
print("x/y=%d"%(x/y))
print("x%%y=%d"%(x%y))
print("x//y=%d"%(x//y))

z=input("id num:")
z=z.replace(z[1:3],'999')
print(z)

z='ABCDEF'
z=':'.join(z)
print(z)
list1=z.split(':')
print(list1)

