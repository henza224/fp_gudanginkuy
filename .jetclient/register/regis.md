```toml
name = 'regis'
method = 'POST'
url = 'http://localhost:8080/auth/login'
sortWeight = 1000000
id = '298f90e5-a0a5-4afc-8cc6-2b7d4e23445f'

[[queryParams]]
key = 'username'
disabled = true

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
  "username" : "ADMIN",
  "password" : "ADMIN"
}'''
```
