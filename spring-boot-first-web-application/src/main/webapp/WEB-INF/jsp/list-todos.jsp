<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<div class="container">

	This is the to-do list for ${name}:


	<table class="table table-striped">
		<thead>
			<tr>
				<th>Description</th>
				<th>Date</th>
				<th>Status</th>
				<th>DELETE</th>
				<th>UPDATE</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.desc}</td>
					<td><fmt:formatDate value="${todo.targetDate}"
							pattern="dd/MM/yyyy" /></td>
					<td>${todo.done}</td>
					<th><a type="button" class="btn btn-warning"
						href="/delete-todo?id=${todo.id}">DELETE</a></th>
					<th><a type="button" class="btn btn-success"
						href="/update-todo?id=${todo.id}">UPDATE</a></th>
				</tr>
			</c:forEach>
		</tbody>

	</table>

	<div>
		<a class="button" href="/addtodo">Add todo</a>
	</div>
</div>
<%@ include file="common/footer.jspf"%>